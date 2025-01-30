import java.awt.*;
import java.awt.datatransfer.*;


public class ClipboardManager {
    private final Clipboard clipboard;

    public ClipboardManager(){
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public String getContentFromClipboard() throws Exception{
        Transferable content = clipboard.getContents(null);

        if (content!= null && content.isDataFlavorSupported(DataFlavor.stringFlavor)){
            return (String) content.getTransferData(DataFlavor.stringFlavor);
        }
        return null;
    }

    public void setTextToClipboard(String text){
        StringSelection stringSelection = new StringSelection(text);
        clipboard.setContents(stringSelection,null);
    }

    public void CleanClipboard(){
        StringSelection stringSelection = new StringSelection("");
        clipboard.setContents(stringSelection,null);
    }
}
