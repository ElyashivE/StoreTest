package DB;

import java.util.Dictionary;
import java.util.Hashtable;

public class CostumerDB
{
    public static Dictionary<String, String> getFakeCustomerDB()
    {
        Dictionary<String, String> customerDB = new Hashtable<>();
        customerDB.put("name", "John Doe");
        customerDB.put("country", "Israel");
        customerDB.put("city", "Jerusalem");
        customerDB.put("card", "1234 5678 1234 5678");
        customerDB.put("month", "07");
        customerDB.put("year", "1984");
        return customerDB;
    }
}
