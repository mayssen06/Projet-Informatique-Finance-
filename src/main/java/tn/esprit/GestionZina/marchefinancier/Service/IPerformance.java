package tn.esprit.GestionZina.marchefinancier.Service;

public interface IPerformance {

    double calculateROI(int id);

    double calculateReturnPortfolio(int id);

    double calculateMaximumDrawdown(int idUser);

    double calculatePortfolioDiversificationRatio(int idUser);

    double calculateMarketSentimentIndex();

    double PortfolioTurnover(int id);

}
