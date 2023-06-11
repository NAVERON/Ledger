package ledgerclient;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ledgerclient.service.UserAcountService;
import ledgerclient.utils.LogicController;
import ledgerclient.views.SplitInformationTabPane;
import ledgerclient.views.UserProfileView;
import ledgerclient.views.VerticalMenuBar;
import ledgerclient.views.VerticalMenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LedgerClientLauncher extends Application {

    private static final Logger log = LoggerFactory.getLogger(LedgerClientLauncher.class);
    
    public static void main(String[] args) {
        System.out.println("Ledger Client Launcher");
        
        Application.launch(args);
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(this.createContent(primaryStage));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ledger Launcher");
        
        primaryStage.setOnCloseRequest(e -> {  // 一些底层服务需要在这里清理 
            UserAcountService.getInstance().destory();
        });
        
        primaryStage.show();
    }
    
    private Parent createContent(Stage primaryStage) {
        BorderPane root = new BorderPane();
        LogicController controller = new LogicController(primaryStage);  // controller 中档所有顶级组件的中间通信器 
        
        // 各个组件绑定controller 相互控制 
        UserProfileView userProfile = new UserProfileView(controller);
        VerticalMenuBar menuBar = new VerticalMenuBar(controller);
        SplitInformationTabPane tabPane = new SplitInformationTabPane(controller);
        
        root.setTop(userProfile);
        root.setLeft(menuBar);
        root.setCenter(tabPane);
        
        menuBar.addMenuItems(new VerticalMenuItem("用户管理"), new VerticalMenuItem("流水管理"));
        // tabPane.getTabs().addAll(new Tab("HELLO"));
        root.setPrefSize(1000, 600);
        // 组件绑定控制中心 
        
        return root;
    }
    
}


