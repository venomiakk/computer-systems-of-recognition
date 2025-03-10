package ksr;

public class Vector {
    String firstExchange;
    int uniqueExchanges;
    String firstPerson;
    int uniquePeople;
    String firstPlace;
    int uniquePlaces;
    String firstSubjectCode;
    int uniqueSubjectCodes;
    String firstEconomicIndicatorCode;
    int uniqueEconomicIndicatorCodes;
    String firstCurrencyCode;
    int uniqueCurrencyCodes;
    String firstCorporateCode;
    int uniqueCorporateCodes;
    String firstCommodityCode;
    int uniqueCommodityCodes;
    String firstEnergyCode;
    int uniqueEnergyCodes;

    public Vector(String firstExchange, int uniqueExchanges,
                  String firstPerson, int uniquePeople,
                  String firstPlace, int uniquePlaces,
                  String firstSubjectCode, int uniqueSubjectCodes,
                  String firstEconomicIndicatorCode, int uniqueEconomicIndicatorCodes,
                  String firstCurrencyCode, int uniqueCurrencyCodes,
                  String firstCorporateCode, int uniqueCorporateCodes,
                  String firstCommodityCode, int uniqueCommodityCodes,
                  String firstEnergyCode, int uniqueEnergyCodes) {

        this.firstExchange = firstExchange;
        this.uniqueExchanges = uniqueExchanges;
        this.firstPerson = firstPerson;
        this.uniquePeople = uniquePeople;
        this.firstPlace = firstPlace;
        this.uniquePlaces = uniquePlaces;
        this.firstSubjectCode = firstSubjectCode;
        this.uniqueSubjectCodes = uniqueSubjectCodes;
        this.firstEconomicIndicatorCode = firstEconomicIndicatorCode;
        this.uniqueEconomicIndicatorCodes = uniqueEconomicIndicatorCodes;
        this.firstCurrencyCode = firstCurrencyCode;
        this.uniqueCurrencyCodes = uniqueCurrencyCodes;
        this.firstCorporateCode = firstCorporateCode;
        this.uniqueCorporateCodes = uniqueCorporateCodes;
        this.firstCommodityCode = firstCommodityCode;
        this.uniqueCommodityCodes = uniqueCommodityCodes;
        this.firstEnergyCode = firstEnergyCode;
        this.uniqueEnergyCodes = uniqueEnergyCodes;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "firstExchange='" + firstExchange + '\'' +
                ", uniqueExchanges=" + uniqueExchanges +
                ", firstPerson='" + firstPerson + '\'' +
                ", uniquePeople=" + uniquePeople +
                ", firstPlace='" + firstPlace + '\'' +
                ", uniquePlaces=" + uniquePlaces +
                ", firstSubjectCode='" + firstSubjectCode + '\'' +
                ", uniqueSubjectCodes=" + uniqueSubjectCodes +
                ", firstEconomicIndicatorCode='" + firstEconomicIndicatorCode + '\'' +
                ", uniqueEconomicIndicatorCodes=" + uniqueEconomicIndicatorCodes +
                ", firstCurrencyCode='" + firstCurrencyCode + '\'' +
                ", uniqueCurrencyCodes=" + uniqueCurrencyCodes +
                ", firstCorporateCode='" + firstCorporateCode + '\'' +
                ", uniqueCorporateCodes=" + uniqueCorporateCodes +
                ", firstCommodityCode='" + firstCommodityCode + '\'' +
                ", uniqueCommodityCodes=" + uniqueCommodityCodes +
                ", firstEnergyCode='" + firstEnergyCode + '\'' +
                ", uniqueEnergyCodes=" + uniqueEnergyCodes +
                '}';
    }
}