package controllers;

import play.Logger;
import play.db.DB;
import play.mvc.Before;
import play.mvc.Controller;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: tdoe
 * Date: 13/01/12
 * Time: 15:19
 * <p/>
 * クラスにWith(DBConnectionChanger.class)アノテーションがついてると呼び出されるクラス
 */
public class DBConnectionChanger extends Controller {

    /**
     * このクラスが呼ばれると実行されるメソッドです。
     * 呼び出し元のクラスかメソッドにDBReadOnlyTrueアノテーションがついてると
     * setReadOnly(true)を実行し
     * DBReadOnlyFalseアノテーションがついていれば
     * setReadOnly(false)を実行します。
     * 呼び出されたがアノテーションがない場合は何もしません。
     *
     * @throws SQLException
     */
    @Before
    public static void checkAnnotation() throws SQLException {

        //リクエストされたコントローラクラスに@DBReadOnlyTrueがついてるかどうか
        DBReadOnlyTrue dbReadOnlyTrueByClass = request.controllerClass.getAnnotation(DBReadOnlyTrue.class);

        //リクエストされたコントローラクラスメソッドに@DBReadOnlyTrueがついてるかどうか
        DBReadOnlyTrue dbReadOnlyTrueByMethod = request.invokedMethod.getAnnotation(DBReadOnlyTrue.class);

        //リクエストされたコントローラクラスに@DBReadOnlyFalseがついてるかどうか
        DBReadOnlyFalse dbReadOnlyFalseByClass = request.controllerClass.getAnnotation(DBReadOnlyFalse.class);

        //リクエストされたコントローラクラスメソッドに@DBReadOnlyFalseがついてるかどうか
        DBReadOnlyFalse dbReadOnlyFalseByMethod = request.invokedMethod.getAnnotation(DBReadOnlyFalse.class);

        //クラスかクラスメソッドにannotationがついてれば実行
        if (dbReadOnlyTrueByClass != null || dbReadOnlyTrueByMethod != null) {
            changeReadOnly(true);
        } else if (dbReadOnlyFalseByClass != null || dbReadOnlyFalseByMethod != null) {
            changeReadOnly(false);
        }
    }

    /**
     * @param isReadOnly DB.getConnection().setReadOnlyにセットしたい値
     * @throws SQLException
     */
    private static void changeReadOnly(boolean isReadOnly) throws SQLException {
        Logger.debug("set parameter is %s", isReadOnly);
        DB.getConnection().setReadOnly(isReadOnly);
        Logger.debug("success! set read only is %s", isReadOnly);
    }
}
