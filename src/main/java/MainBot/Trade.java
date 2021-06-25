package MainBot;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Trade {

    public static String getValuta(String valuta, Service service) throws IOException {

            URL url = new URL("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5");

        Scanner i = new Scanner((InputStream) url.getContent());
        String result = "";
        while (i.hasNext()){
            result += i.nextLine();
        }

        JSONArray array = new JSONArray(result);
        for (int j = 0; j < array.length() ; j++) {
            JSONObject obj = array.getJSONObject(j);

            if(obj.getString("ccy").equals(valuta)) {
                service.setCcy(obj.getString("ccy"));
                service.setBase_ccy(obj.getString("base_ccy"));
                service.setBuy(obj.getDouble("buy"));
                service.setSale(obj.getDouble("sale"));
            }
        }
        return "Курс "+valuta+ " в Приват24" +"\n"+"Покупка: "+ service.getBuy()+" UAH"+"\n"+"Продажа: "+ service.getSale()+" UAH";
    }
}

