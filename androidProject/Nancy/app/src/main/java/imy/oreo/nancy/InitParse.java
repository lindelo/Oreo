package imy.oreo.nancy;
import android.content.Context;

import com.parse.*;
public class  InitParse {

    private static InitParse parse;
    private Context context;

    private InitParse(){}

    public static void  initParse(Context c) {

        if(parse == null) {

            Parse.initialize(c, "kklfSNNgmK7kCdlIxzYzMHK7f6PZXbWhDkU0I2Q8", "zShNGOBu2cMzoiqhEWOW8PSue4K8pXNkgtm140Ow");
            parse = new InitParse();
        }
    }

}
