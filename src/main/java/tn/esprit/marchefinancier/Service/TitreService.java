package tn.esprit.marchefinancier.Service;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import tn.esprit.marchefinancier.Entites.Portefeuille;
import tn.esprit.marchefinancier.Entites.Societe;
import tn.esprit.marchefinancier.Entites.Titre;
import tn.esprit.marchefinancier.Repositories.PortefeuilleRepository;
import tn.esprit.marchefinancier.Repositories.SocieteRepository;
import tn.esprit.marchefinancier.Repositories.TitreRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TitreService implements ITitre{
    public TitreRepository titreRepository;
    public PortefeuilleRepository portefeuilleRepository;
    public SocieteRepository societeRepository;




    @Override
    public Titre edit(Titre a) {
        return titreRepository.save(a);
    }

    @Override
    public List<Titre> selectAll() {
        return titreRepository.findAll();
    }

    @Override
    public Titre SelectById(int id) {
        return titreRepository.findById(id).get();
    }

    @Override
    public void deleteById(int id) {
        titreRepository.deleteById(id);

    }


    @Override
    public Titre add(Titre a) {

        Societe societe=societeRepository.findById(a.getSociete().getIdSociete()).get();
        societe.setTitre(a);
        a.setNominal(societe.getCapitalBoursier()/societe.getNbrTitre());
        a.setDernier(a.getNominal());
        a.setBas(a.getNominal());
        a.setHaut(a.getNominal());
        a.setOuverture(a.getNominal());
        a.setPrix(a.getNominal());

        titreRepository.save(a);

        societeRepository.save(societe);
        return a;
    }


    @Override
    @Transactional
    public List<Titre> gettitrebyPortefeuille(int id) {
        System.out.println("aaaaa");
        System.out.println(id);
        Portefeuille p= portefeuilleRepository.findByUserIdUser(id);
        System.out.println("vvvvvvv");
        System.out.println(p.getIdPortefeuille());
        return titreRepository.gettitrebyportefeuille(p.getIdPortefeuille());
    }

    @Override
    public Titre findbyidSociete(int id) {
        return titreRepository.findBySocieteIdSociete(id);
    }

    public double VariationAction(int idtitre) {
        int vente = ordreRepository.OffreParTitre(idtitre);
        int achat = ordreRepository.DemandeParTitre(idtitre);
       /* double critereeconomique;
         if(achat-vente * volatilité>0)
        (Quantité d'Achat - Quantité de Vente du jour *30 +- variance mensuelle ) * Volatilité Historique * critereeconomique
          else  (Quantité d'Achat - Quantité de Vente du jour *30 +- variance mensuelle ) * Volatilité Historique * -critereeconom
*/
        return 0;
    }

    @Override
    public double RisqueMarkowvitz(int idtitre) throws IOException {
        UserExcelExporter excelExporter = new UserExcelExporter();
        double  rendement=excelExporter.GetRendementMoyeParTitre(idtitre);
        Titre titre=titreRepository.findTitresByIdTitre(idtitre);
        double volatilite=calculateHistoricalVolatility(titre);
        if(rendement==0){
            rendement=1;
        }
        double critere=volatilite/rendement;
        return critere;
    }

    public double simulateNextPrice( Titre titre, double riskFreeRate , double volatility) {
       double initialPrice = titre.getNominal();
        int n = ordreRepository.DemandeParTitre(titre.getIdTitre());
        int m = ordreRepository.OffreParTitre(titre.getIdTitre());
        int p = societeRepository.findByTitreIdTitre(titre.getIdTitre()).getNbrTitre();
        double bonus = n-m/p;
        Random random = new Random();

        double dt = 1.0 / 252.0; // Daily time step (252 trading days in a year)
        double z = random.nextGaussian(); // Standard normal random variable

        double drift = (riskFreeRate - 0.5 * Math.pow(volatility, 2)) * dt;
        double diffusion = volatility * Math.sqrt(dt) * z;

        double nextPrice = initialPrice * (Math.exp(drift + diffusion) + bonus ) ;


        return nextPrice;
    }
    public double calculateHistoricalVolatility(Titre titre) throws IOException {
        // Check if there is enough historical price data for calculation
        UserExcelExporter excelExporter = new UserExcelExporter();
        List<Double> historicalPrices = excelExporter.getHistoricalPricesForAsset(titre.getIdTitre());
        int n = historicalPrices.size();
        if (n < 1) {
            return 0.0;  // Not enough data for meaningful volatility
        }

        // Calculate daily returns
        List<Double> dailyReturns = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            double todayPrice = historicalPrices.get(i);
            double yesterdayPrice = historicalPrices.get(i - 1);
            double dailyReturn = (todayPrice - yesterdayPrice) / yesterdayPrice;
            dailyReturns.add(dailyReturn);
        }

        // Calculate the mean of daily returns
        double sumReturns = 0.0;
        for (double returnVal : dailyReturns) {
            sumReturns += returnVal;
        }
        double meanReturn = sumReturns / n;

        // Calculate the squared differences from the mean
        double sumSquaredDifferences = 0.0;
        for (double returnVal : dailyReturns) {
            double diffFromMean = returnVal - meanReturn;
            sumSquaredDifferences += diffFromMean * diffFromMean;
        }

        // Calculate variance as the mean of squared differences
        double variance = sumSquaredDifferences / (n - 1);

        // Calculate the standard deviation (volatility) as the square root of variance
        double volatility = Math.sqrt(variance);

        return volatility;
    }
    @Scheduled(fixedRate = 60000)
    public double UpdateCotation() throws IOException {

        List<Titre> titres = titreRepository.findAll();
        for (Titre titre : titres) {

            double volatility = calculateHistoricalVolatility(titre);
            System.out.println("volatility");
            System.out.println(volatility);
            double nextPrice = simulateNextPrice(titre,0.08,volatility);
            titre.setPrix(nextPrice) ;
            if (nextPrice > titre.getHaut()) {
                titre.setHaut(nextPrice);
            }

            if (nextPrice < titre.getBas()) {
                titre.setBas(nextPrice);
            }
            titre.setOuverture(titre.getDernier());
            titre.setDernier(nextPrice);
            titreRepository.save(titre);
            UserExcelExporter excelExporter = new UserExcelExporter();
            excelExporter.ajouterLigneDansExcel(titre);
        }

        return 0;
    }





}
