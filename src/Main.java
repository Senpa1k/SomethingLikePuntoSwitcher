import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            ClipboardManager clipboardManager = new ClipboardManager();
            KeyboardEmulator keyboardEmulator = new KeyboardEmulator();
            Scanner scanner = new Scanner(System.in);
            Translator translator = new Translator();
            String prevText = "";

            System.out.println("Введите язык с которого переводите");
            String nativeLang = scanner.nextLine();
            System.out.println("Введите язык на который переводить");
            String neededLang = scanner.nextLine();

            clipboardManager.CleanClipboard();

            while (true){
                String text = clipboardManager.getContentFromClipboard();
                if (text != null && !text.isEmpty() && !prevText.equals(text)) {
                    text = translator.translate(nativeLang,neededLang,text);
                    clipboardManager.setTextToClipboard(text);

                    keyboardEmulator.PressingCtrlV();

                    prevText = text;
                }

                Thread.sleep(500);
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }



    }
}
