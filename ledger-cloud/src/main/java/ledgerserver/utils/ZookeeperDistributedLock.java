package ledgerserver.utils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


public class ZookeeperDistributedLock implements Lock, Watcher {

    private ZooKeeper zk = null;
    
    private String lockKeySuffix = "_lock_";
    private String resourceName = null;
    
    private String ROOT_PATH = "/lock";
    private String CURRENT_LOCK = null;
    private String WAIT_LOCK = null;
    
    private CountDownLatch countDownLatch = null;
    private Integer waitLockTimeout = 1000 * 30;
    
    public ZookeeperDistributedLock(String config, String resourceName) {
        try {
            this.zk = new ZooKeeper(config, this.waitLockTimeout, this);
            Stat stat = this.zk.exists(this.ROOT_PATH, false);
            if(stat == null) {
                this.zk.create(this.ROOT_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Override
    public void process(WatchedEvent event) {
        // TODO Auto-generated method stub
        if(event.getPath().startsWith(this.ROOT_PATH) && event.getType() == EventType.NodeDeleted) {
            if(this.countDownLatch != null) {
                this.countDownLatch.countDown();
            }
        }
        
    }

    @Override
    public void lock() {
        // TODO Auto-generated method stub
        if(this.tryLock()) {
            // 获得了锁 
            return;
        }else {
            this.waitForLock(this.WAIT_LOCK, 1000 * 10L);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        // TODO Auto-generated method stub
        this.lock();
    }

    @Override
    public boolean tryLock() {
        // TODO Auto-generated method stub
        try {
            CURRENT_LOCK = this.zk.create(
                    this.ROOT_PATH + "/" + this.resourceName + this.lockKeySuffix, 
                    new byte[0], 
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, 
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> subNodes = this.zk.getChildren(this.ROOT_PATH, false);
            List<String> lockPaths = new ArrayList<>();
            subNodes.forEach(node -> {
                String realNodeName = node.split(this.lockKeySuffix)[0];
                if(realNodeName.equals(this.resourceName)) {
                    lockPaths.add(node);
                }
            });
            Collections.sort(lockPaths);
            if(this.CURRENT_LOCK.equals(this.ROOT_PATH + "/" + lockPaths.get(0))) {
                return true;
            }
            
            String waitingNode = this.CURRENT_LOCK.substring(this.CURRENT_LOCK.lastIndexOf("/") + 1);
            this.WAIT_LOCK = lockPaths.get(Collections.binarySearch(lockPaths, waitingNode) - 1);
            
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        // TODO Auto-generated method stub
        if(this.tryLock()) {
            return true;
        }
        
        return this.waitForLock(this.WAIT_LOCK, 1000 * 10L);
    }
    
    public boolean waitForLock(String waitingNode, Long waitTime) {
        try {
            Stat stat = this.zk.exists(this.ROOT_PATH + "/" + waitingNode, true);
            
            if(stat != null) {
                this.countDownLatch = new CountDownLatch(1);
                this.countDownLatch.await();
                
                this.countDownLatch = null;
                
                return true;
            }
        } catch (KeeperException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return false;
    }

    @Override
    public void unlock() {
        // TODO Auto-generated method stub
        try {
            this.zk.delete(this.CURRENT_LOCK, -1);
            this.CURRENT_LOCK = null;
            this.zk.close();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeeperException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public Condition newCondition() {
        // TODO Auto-generated method stub
        return null;
    }

}





