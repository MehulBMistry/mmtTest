package makemytriptest;

import base.BaseTests;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.MakeMyTripeFlightsPage;

import static org.testng.Assert.assertEquals;

public class makeMyTripTest extends BaseTests
{
    @Test
    public void makeMyTripFlightSearch(){
        MakeMyTripeFlightsPage makeMyTripeFlightsPage = homePage.openMMTSite();
        SoftAssert softAssert = new SoftAssert();
        
        //Select From – Ahmedabad & To - Pune 

        makeMyTripeFlightsPage.selectFromCity("Ahmedabad");
        makeMyTripeFlightsPage.selectCityList("Ahmedabad");
        makeMyTripeFlightsPage.selectToCity("Pune");
        makeMyTripeFlightsPage.selectCityList("Pune");

        //makeMyTripeFlightsPage.selectDeparture();
        //Select Departure date as 1st date of next month
        makeMyTripeFlightsPage.selectDepartureDate();
        
        //Select ADULTS –2, CHILDREN - 1, INFANTS – 1
        makeMyTripeFlightsPage.selectTravellers();
        makeMyTripeFlightsPage.selectAdults(2);
        makeMyTripeFlightsPage.selectChildren(2);
        makeMyTripeFlightsPage.selectInfants(2);
        makeMyTripeFlightsPage.clickApply();

        //Click on search
        makeMyTripeFlightsPage.clickSearch();
        
        //Verify TRIP TYPE, FROM, TO, DEPART & PASSENGERS & CLASS 
        softAssert.assertEquals(makeMyTripeFlightsPage.getTripType(),"One Way", "Trip type is different");
        softAssert.assertEquals(makeMyTripeFlightsPage.getFromCity(),"Ahmedabad, India", "Trip type is different");
        softAssert.assertEquals(makeMyTripeFlightsPage.getToCity(),"Pune, India", "Trip type is different");
        softAssert.assertEquals(makeMyTripeFlightsPage.getDeparture(),"Mon, May 1, 2023", "Trip type is different");
        softAssert.assertEquals(makeMyTripeFlightsPage.getPassengerClass(),"4 Travellers, Economy", "Trip type is different");

        //Filter By One Way Price and validate all flight prices are between that range. Eg filter by price 3,486 to 9,000, all the flight price is between 3,486 to 9,000.
        makeMyTripeFlightsPage.setOneWayPrice(9000);
        softAssert.assertTrue(makeMyTripeFlightsPage.getListPrice(9000));
        
        //Filter by Nonstop and validate count given beside Nonstop in filer and right-side total flights count are same. eg Beside Nonstop it is showing 3 and on right side it is showing 3 flights.
        makeMyTripeFlightsPage.selectStops("Non Stop");
        softAssert.assertEquals(makeMyTripeFlightsPage.getStopWiseFlightCount("Non Stop"),makeMyTripeFlightsPage.getFlightListCount(),"Count are different");

        softAssert.assertAll();

    }

}
