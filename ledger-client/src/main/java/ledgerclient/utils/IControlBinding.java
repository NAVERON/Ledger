package ledgerclient.utils;

/**
 * 提供给控制  控制器和各个组件绑定 
 * @author eron 
 * 使用字符串标识或者 long类型的id 需要其他方法标识组件的实际含义 
 */
public interface IControlBinding {

    public void bindingNodes(IEventBinding... nodes);  // 控制中心主动绑定 
    public void bindACK(String componentID, IEventBinding node);  // 真正加入map 由组件调用 作为确认消息, 表示组件确定加入 
    public void unBindNode(IEventBinding node);
    public void unBindNode(String componentID);
    
}
