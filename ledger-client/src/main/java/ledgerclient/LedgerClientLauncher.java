package ledgerclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ledgerclient.utils.LogicController;
import ledgerclient.views.SplitInformationTabPane;
import ledgerclient.views.UserProfileView;
import ledgerclient.views.VerticalMenuBar;
import ledgerclient.views.VerticalMenuItem;




public class LedgerClientLauncher extends Application {

    private static final Logger log = LoggerFactory.getLogger(LedgerClientLauncher.class);
    
    public static void main(String[] args) {
        System.out.println("Ledger Client Launcher");
        
        Application.launch(args);
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(this.createContent());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ledger Launcher");
        
        primaryStage.show();
    }
    
    private Parent createContent() {
        BorderPane root = new BorderPane();
        LogicController controller = new LogicController();
        
        UserProfileView userProfile = new UserProfileView();
        VerticalMenuBar menuBar = new VerticalMenuBar(new VerticalMenuItem("用户管理"));
        SplitInformationTabPane tabPane = new SplitInformationTabPane(new Tab("测试"), new Tab("测试2"));
        
        root.setTop(userProfile);
        root.setLeft(menuBar);
        root.setCenter(tabPane);
        
        menuBar.addMenuItems(new VerticalMenuItem("FIRST"), new VerticalMenuItem("SECOND"));
        root.setPrefSize(1000, 600);
        // 组件绑定控制中心 
        controller.bindingNodes(userProfile, menuBar, tabPane);
        
        return root;
    }
}


