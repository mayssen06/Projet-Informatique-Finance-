package tn.esprit.GestionMortadha.marchefinancier.Service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.esprit.GestionMortadha.marchefinancier.Repositories.PortefeuilleRepository;



@AllArgsConstructor
@Service
@Transactional
@Slf4j

public class PerformanceService implements IPerformance {
    public PortefeuilleRepository portefeuilleRepository;
    public SocieteService societeService;
    public UserService userService;

    public double calculateROI(int id) {
        return ((userService.getUserById(id).getSolde() - 50000 ) / 50000) * 100;
    }
    public double calculateReturnPortfolio ( int id ) {
        return (userService.getUserById(id).getSolde()-50000) / 100 ;
    }

    public double calculateMaximumDrawdown(int id) {
        return ((userService.getUserById(id).getHighestsold() - userService.getUserById(id).getLowestsold()) / userService.getUserById(id).getHighestsold()) * 100;
    }

    public double calculatePortfolioDiversificationRatio(int idUser) {
        return  portefeuilleRepository.findByUserIdUser(idUser).getTitreListP().size() / societeService.selectAll().size();
    }




    public double calculateMarketSentimentIndex() {
        return ((double) (13 - 7) / 20);
    }

    public double PortfolioTurnover(int id) {
        return userService.getUserById(id).getTransactionListA().size()/ ( userService.getUserById(id).getTransactionListV().size() * userService.getUserById(id
        ).getSolde());
    }


      /*public double calculateSharpeRatio(//double portfolioStdDev) {
        return  (calculateReturnPortfolio() - 0.08) / portfolioStdDev;
    }*/





    /*public double calculateAlpha(  double beta, double marketReturn) {
        return calculateReturnPortfolio() - (0.08 + beta * (marketReturn - 0.08));
    }*/

    /*public double calculateValueAtRisk(double portfolioValue, double zScore, double portfolioStdDev) {
        return portfolioValue * zScore * portfolioStdDev;
    }*/

   /* public double calculateConditionalValueAtRisk(double expectedLoss, double portfolioStdDev) {
        return expectedLoss + 0.5 * portfolioStdDev;
    }









}

