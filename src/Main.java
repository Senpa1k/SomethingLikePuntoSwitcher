

public class Main {
    public static void main(String[] args) {
        try {
            ClipboardManager clipboardManager = new ClipboardManager();
            KeyboardEmulator keyboardEmulator = new KeyboardEmulator();
            String prevText = "";

            clipboardManager.CleanClipboard();

            while (true){
                String text = clipboardManager.getContentFromClipboard();
                if (text != null && !text.isEmpty() && !prevText.equals(text)) {

                    text = text.toUpperCase();
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
