/*package tn.esprit.GestionMortadha.marchefinancier.RestControllers;


import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.GestionMortadha.marchefinancier.Service.IPerformance;

@RestController
@AllArgsConstructor
@CrossOrigin("*")

public class PerformanceController {

    IPerformance iPerformance;


    @GetMapping("/calculate-roi/{id}" )
    public double calculateROI(@PathVariable int id) {
        return iPerformance.calculateROI(id);
    }

    @GetMapping("/calculate-return-portfolio/{id}")
    public double calculateReturnPortfolio(@PathVariable int id) {
        return iPerformance.calculateReturnPortfolio(id);
    }

    @GetMapping("/calculate-maximum-drawdown/{id}")
    public double calculateMaximumDrawdown(@PathVariable int id) {
        return iPerformance.calculateMaximumDrawdown(id);
    }

    @GetMapping("/calculate-portfolio-diversification-ratio/{id}")
    public double calculatePortfolioDiversificationRatio(@PathVariable int id) {
        return iPerformance.calculatePortfolioDiversificationRatio(id);
    }

    @GetMapping("/calculate-market-sentiment-index")
    public double calculateMarketSentimentIndex() {
        // Pass your parameters here (numPositiveNewsMentions, numNegativeNewsMentions, totalNewsMentions)
        return iPerformance.calculateMarketSentimentIndex();
    }

    @GetMapping("/calculate-portfolio-turnover/{id}")
    public double calculatePortfolioTurnover(@PathVariable int id) {
        return iPerformance.PortfolioTurnover(id);
    }




}*/
