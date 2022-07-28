package ledgerclient.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.control.Tab;
import javafx.stage.Stage;
import ledgerclient.service.BusinessFlowService;
import ledgerclient.service.UserAcountService;
import ledgerclient.views.SplitInformationTabPane;
import ledgerclient.views.UserProfileView;
import ledgerclient.views.VerticalMenuBar;

/**
 * 控制字介面的协调关系 
 * @author eron
 * 总体设计, 作为一个顶层的控制器 分别管控传入的node和 parent 组件, 实现各自的点击动作传递 
 * 各个组件必须有一个id参数, id各自生成, 使用统一接口实现一个接口生成  
 */
public class LogicController {

    // 思考一个大问题 如何保证各个组件之间的通信正确呢 ? 
    private static final Logger log = LoggerFactory.getLogger(LogicController.class);

    private UserProfileView userProfile;
    private VerticalMenuBar menuBar;
    private SplitInformationTabPane informationTabPane;
    
    private Stage primaryStage;
    
    public LogicController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    public void linkUserProfile(UserProfileView userProfile) {
        this.userProfile = userProfile;
    }
    public void linkMenuBar(VerticalMenuBar menuBar) {
        this.menuBar = menuBar;
    }
    public void linkInformationTabPane(SplitInformationTabPane informationTabPane) {
        this.informationTabPane = informationTabPane;
    }
    
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
    
    public void unSelectAllMenuItems() {
        this.menuBar.unSelectAll();
    }
    
    public Tab makeSPlitInformationCreateTab(String tabName) {
        return this.informationTabPane.createTab(tabName);
    }
    
    public String getUserinformation() {
        return this.userProfile.getUser().toString();
    }
    
    public String getRecentBusiness() {
        return BusinessFlowService.getInstance().getRecentBusinessFlow(UserAcountService.currentUserID);
    }
}









