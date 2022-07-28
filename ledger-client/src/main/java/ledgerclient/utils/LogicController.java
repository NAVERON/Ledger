package ledgerclient.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.layout.HBox;
import ledgerclient.views.VerticalMenuBar;
import ledgerclient.views.VerticalMenuItem;

/**
 * 控制字介面的协调关系 
 * @author wangy
 * 总体设计, 作为一个顶层的控制器 分别管控传入的node和 parent 组件, 实现各自的点击动作传递 
 * 各个组件必须有一个id参数, id各自生成, 使用统一接口实现一个接口生成  
 */
public class LogicController implements IControlBinding {

    // 思考一个大问题 如何保证各个组件之间的通信正确呢 ? 
    
    private static final Logger log = LoggerFactory.getLogger(LogicController.class);
    private Map<String, IEventBinding> nodeMap = new HashMap<>();

    @Override
    public void bindingNodes(IEventBinding... nodes) {
        Arrays.asList(nodes).stream().forEach(node -> {
            node.binding(this);
            // this.nodeMap.put(node.getComponentID(), node);
        });
    }
    
    @Override 
    public void bindACK(String componentID, IEventBinding node) {
        this.nodeMap.put(componentID, node);
    }

    @Override
    public void unBindNode(String componentID) {
        IEventBinding node = this.nodeMap.get(componentID);
        if(node == null) {
            return;
        }
        node.unBinding();
        this.nodeMap.remove(componentID);
    }

    @Override
    public void unBindNode(IEventBinding node) {
        this.nodeMap.get(node.getComponentID()).unBinding();
    }
    
    // 需要设计统一沟通模式 
    public void checkoutSpecialNode() {
        // 这种类似的结构可以实现 但是很生硬 
        this.nodeMap.entrySet().stream()
        .filter(x -> x.getValue().getClass().isInstance(VerticalMenuBar.class))
        .map(x ->(VerticalMenuBar)x)
        .forEach(x -> x.makeItemUnselect());
    }
    
    @Override 
    public void commandTransfer(String from, String command) {
        log.info("从哪里来， 到哪里去， 执行什么命令 : {}, {}", from, command);
        if(command.equals("CLEAR_ALL_MENUITEMs")) {
            this.nodeMap.get("BAR").executeCommand(from, from, command);
        }
    }
}









