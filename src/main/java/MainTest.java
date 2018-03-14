import com.jme3.app.SimpleApplication;

/**
 * @Auth:chenxinghua
 * @Date:2018\3\14 0014 17:18
 * @Description:
 */
public class MainTest extends SimpleApplication {
    public void simpleInitApp() {
        System.out.println("dsa");
    }

    @Override
    public void start() {
        System.out.println("start..");
        super.start();
    }

    @Override
    public void update() {
        System.out.println("update");
        super.update();
    }

    @Override
    public void destroy() {
        System.out.println("destory");
        super.destroy();
    }

    public static void main(String[] args){
        MainTest mainTest = new MainTest();
        mainTest.start();
    }
}
