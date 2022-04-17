import org.w3c.dom.Text;
import ru.netology.graphics.image.BadImageSizeException;
import ru.netology.graphics.image.TextColorSchema;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.server.GServer;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;


public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new Convert();
        GServer server = new GServer(converter);
        server.start();

        String url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR51DjYZD3PoYtyn172-aM6SvUadKqGDIpWiQ&usqp=CAU";
        String imgTxt = converter.convert(url);
        System.out.println(imgTxt);
    }
}
