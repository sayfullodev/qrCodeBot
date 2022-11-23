package botboy;

import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.*;

public class Botjon extends TelegramWebhookBot implements LongPollingBot {

    Set<Long> humansSize = new HashSet<>();

    Map<Long, String> qrCode = new HashMap<>();

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(new Botjon());
        } catch (TelegramApiException e) {
            System.err.println("nimadir xato");
        }
    }

    @Override
    public String getBotUsername() {
        return "CreatingQr_bot";
    }

    @Override
    public String getBotToken() {
        return "5667776087:AAHV6QbKVxkUaNg4SpM_NCyU0sUSijxIhb0";
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotPath() {
        return null;
    }


    @Override
    public void onUpdateReceived(Update update) {
        Methods methods = new Methods();
        SendMessage sendMessage = new SendMessage();
        Message message = update.getMessage();
        String text = message.getText();
        Long chatId = message.getChatId();
        if (message.hasText()) {
            if (text.equals("/start")) {
                sendMsg(sendMessage, "salom hush kelibsiz " + message.getFrom().getUserName(), chatId);
                buttonYasash(sendMessage, chatId);
                humansSize.add(chatId);
            } else if (text.equals("create QrCode🏁")) {
                sendMsg(sendMessage, "qr kodni yaratish uchun yozing", chatId);
                qrCode.put(chatId, "qrCode");
            } else if (text.equals("statistika💹")) {
                sendMsg(sendMessage, "bizning botimizda " + humansSize.size() + "ta foydalanuvchi mavjud", chatId);
            } else {
                if (qrCode.get(chatId).equals("qrCode")) {
                    methods.qrcode(text);
                    getQrCode(chatId);
                    qrCode.remove(chatId);
                } else if (!qrCode.get(chatId).equals("qrCode")) {
                    sendMsg(sendMessage, "ushbu bo'lim mavjud emas", chatId);
                }
            }
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.err.println(botboy.Message.NOT_EXECUTE);
        }
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {

    }

    //start methods qozi
    public void sendMsg(SendMessage sendMessage, String text, long chatId) {
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
    }


    public void buttonYasash(SendMessage sendMessage, long chatId) {
        sendMessage.setChatId(chatId);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("create QrCode🏁");
        row.add("statistika💹");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }

    public void getQrCode(long chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        File file = new File("qr_code.jpg");
        InputFile inputFile = new InputFile(file);
        sendPhoto.setPhoto(inputFile);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            System.err.println(botboy.Message.NOT_EXECUTE);
        }
    }

    //end methods qozi
}
