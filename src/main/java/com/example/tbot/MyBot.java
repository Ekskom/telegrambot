package com.example.tbot;
import org.glassfish.jersey.jaxb.internal.XmlJaxbElementProvider;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MyBot extends TelegramLongPollingBot {

    private static final String BOTNAME = "eugenemap_bot";

    private String getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(XmlJaxbElementProvider.App.class.getClassLoader().getResourceAsStream("config2.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("TOKEN");
    }

    @Override
    public String getBotUsername() {
        return BOTNAME;
    }

    @Override
    public String getBotToken() {
        return getProperties();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String text = update.getMessage().getText();

        Service service = new Service();
        Message message = update.getMessage();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        if (message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    sendMsg(message, "\t"+"Hi"+ Emojis.BLUSH+", I am a Telegram Bot and I can show in real time the EXCHANGE RATE IN PRIVAT24, " +
                            "you just need to select a currency."+"\n"+ "\n"+ "\t"+
                            "Also i can calculate the amount of currency, " +
                            "for example, i can MULTIPLY, input: 100x4.5, output: 450"+ Emojis.DOLLAR+"." +"\n"+
                            "DIVIDE, input:400/100.0, output: 4"+ Emojis.DOLLAR +"." +"\n"+ "\n"+"\t"+
                            "That's all! Good luck!" + Emojis.RAISED_HANDS);
                    break;
                case "/help":
                    sendMsg(message, "How i can help?");
                    break;
                case "/settings":
                    sendMsg(message, "Settings");
                    break;
                case "USD":
                    try {
                        sendMsg(message, Trade.getValuta(message.getText(), service)+ "\n"+ "1 UAH = " + String.format("%.3f", 1/service.getBuy())+ " USD");
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                case "RUR":
                    try {
                        sendMsg(message, Trade.getValuta(message.getText(), service)+ "\n"+ "1 UAH = " + String.format("%.3f", 1/service.getBuy())+ " RUR");
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                case "EUR":
                    try {
                        sendMsg(message, Trade.getValuta(message.getText(), service ) + "\n"+ "1 UAH = " + String.format("%.3f", 1/service.getBuy())+ " EUR");
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                default:
                    try {
                        sendMessage.setText("Result: " + getMsg(text) + " conventional units.");
                        execute(sendMessage);
                    } catch (NumberFormatException | TelegramApiException e) {
                        System.out.println("Not number");
                    }
            }
        }
    }

    private double getMsg(String msg) {
        int index;
        int res = 0;
        int res1 = 0;
        index = msg.indexOf('/');
        System.out.println(index);

        if (index > 0) {
            String[] str = msg.split("/");
            Double[] doubles = new Double[str.length];
            for (String strs : str) {
                doubles[res] = Double.parseDouble(strs);
                res++;
            }
            double sum = doubles[0] / doubles[1];

            return sum;
        } else {
            String[] str1 = msg.split("x");
            Double[] doubles = new Double[str1.length];
            for (String strs : str1) {
                doubles[res1] = Double.parseDouble(strs);
                res1++;
            }
            double sum1 = doubles[0] * doubles[1];
            return sum1;


        }
    }


        private void sendMsg (Message message, String text){

            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.setText(text);
            try {
                keyboard(sendMessage);
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    private void keyboard (SendMessage sendMessage){
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);

            List<KeyboardRow> keys = new ArrayList<>();

            KeyboardRow row1 = new KeyboardRow();

            row1.add(new KeyboardButton("USD"));
            row1.add(new KeyboardButton("RUR"));
            row1.add(new KeyboardButton("EUR"));

            keys.add(row1);

            replyKeyboardMarkup.setKeyboard(keys);

        }
    }
