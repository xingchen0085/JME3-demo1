package cn.myxinge.demo;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * @Auth:chenxinghua
 * @Date:2018\3\15 0015 11:46
 * @Description: 测试类
 */
public class HelloJME3 extends SimpleApplication {

    public static void main(String[] args) {
        HelloJME3 helloJME3 = new HelloJME3();
        helloJME3.start();
    }

    //初始化，仅执行一次
    public void simpleInitApp() {
        System.out.println("系统初始化");

        //来个方块
        Box box = new Box(1,1,1);
        Geometry geometry = new Geometry("box",box);
        Material material = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        geometry.setMaterial(material);
        rootNode.attachChild(geometry);
    }
}
