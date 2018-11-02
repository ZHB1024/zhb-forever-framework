package com.zhb.forever.framework.zookeeper.client.impl;

import com.zhb.forever.framework.zookeeper.Constants;
import com.zhb.forever.framework.zookeeper.client.ZookeeperClient;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.I0Itec.zkclient.DataUpdater;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年11月2日下午4:32:05
*/

public class ZookeeperClientImpl implements ZookeeperClient {
    
    private static Logger logger = LoggerFactory.getLogger(ZookeeperClientImpl.class);

    // 创建 ZooKeeper 实例
    private ZooKeeper zk;
    
    // 创建 Watcher 实例
    private Watcher wh = new Watcher() {
        /**
         * Watched事件
         */
        public void process(WatchedEvent event) {
            logger.info("WatchedEvent >>> " + event.toString());
        }
    };
    
    
    public void test(HttpServletRequest request,HttpServletResponse response) throws IOException, InterruptedException, KeeperException{
        createZKInstance();
        ZKOperations();
        ZKClose();
        
    }
    
    // 初始化 ZooKeeper 实例
    private void createZKInstance() throws IOException {
        // 连接到ZK服务，多个可以用逗号分割写
        zk = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", Constants.ZOOKEEPER_SESSION_TIMEOUT, this.wh);
        if(!zk.getState().equals(States.CONNECTED)){
            while(true){
                if(zk.getState().equals(States.CONNECTED)){
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void ZKOperations() throws IOException, InterruptedException, KeeperException {
        Stat stat = zk.exists(Constants.ZOOKEEPER_ROOT_PATH, null);
        if (null == stat) {
            zk.create(Constants.ZOOKEEPER_ROOT_PATH , null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.info("创建根节点：{}", Constants.ZOOKEEPER_ROOT_PATH);
        }
        
        stat = zk.exists(Constants.ZOOKEEPER_ROOT_PATH + "/zoo1", null);
        if (null == stat) {
            zk.create(Constants.ZOOKEEPER_ROOT_PATH + "/zoo1", "小张".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.info("创建{}节点,数据为：{}", new Object[]{"zoo1","小张"});
        }
        
        stat = zk.exists(Constants.ZOOKEEPER_ROOT_PATH + "/zoo2", null);
        if (null == stat) {
            zk.create(Constants.ZOOKEEPER_ROOT_PATH + "/zoo2", "小王".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.info("创建{}节点,数据：{},权限： {} ，节点类型： {}", new Object[]{"zoo2","小王",Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT});
        }
        
        
        /*logger.info("\n1. 创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
        zk.create(ROOT_PATH + "/zoo2", "myData2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);*/
        
        List<String> childerns = zk.getChildren(Constants.ZOOKEEPER_ROOT_PATH, null);
        if (null != childerns) {
            for (String childern : childerns) {
                logger.info("{}/{}: {}",new Object[]{Constants.ZOOKEEPER_ROOT_PATH,childern,new String(zk.getData(Constants.ZOOKEEPER_ROOT_PATH+"/"+childern, null, null))});
            }
            
            for (String childern : childerns) {
                zk.delete(Constants.ZOOKEEPER_ROOT_PATH + "/" + childern, -1);
                logger.info("删除节点：{}",Constants.ZOOKEEPER_ROOT_PATH + "/" + childern);
            }
        }

        /*logger.info("\n2. 查看是否创建成功： ");
        logger.info(new String(zk.getData(ROOT_PATH + "/zoo2", this.wh, null)));// 添加Watch

        // 前面一行我们添加了对/zoo2节点的监视，所以这里对/zoo2进行修改的时候，会触发Watch事件。
        logger.info("\n3. 修改节点数据 ");
        zk.setData(ROOT_PATH + "/zoo2", "shanhy20160310".getBytes(), -1);

        // 这里再次进行修改，则不会触发Watch事件，这就是我们验证ZK的一个特性“一次性触发”，也就是说设置一次监视，只会对下次操作起一次作用。
        logger.info("\n3-1. 再次修改节点数据 ");
        zk.setData(ROOT_PATH + "/zoo2", "shanhy20160310-ABCD".getBytes(), -1);

        logger.info("\n4. 查看是否修改成功： ");
        logger.info(new String(zk.getData(ROOT_PATH + "/zoo2", false, null)));

        logger.info("\n5. 删除节点 ");
        zk.delete(ROOT_PATH + "/zoo2", -1);

        logger.info("\n6. 查看节点是否被删除： ");
        logger.info(" 节点状态： [" + zk.exists(ROOT_PATH + "/zoo2", false) + "]");*/
    }

    private void ZKClose() throws InterruptedException {
        zk.close();
    }
    
    /*public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZookeeperController dm = new ZookeeperController();
        dm.createZKInstance();
        dm.ZKOperations();
        dm.ZKClose();
    }*/

    
    /*public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
        String node = "/myapp";

        // 订阅监听事件
        childChangesListener(zkClient, node);
        dataChangesListener(zkClient, node);
        stateChangesListener(zkClient);

        if (!zkClient.exists(node)) {
            zkClient.createPersistent(node, "hello zookeeper");
        }
        logger.info(zkClient.readData(node).toString());

        zkClient.updateDataSerialized(node, new DataUpdater<String>() {

            public String update(String currentData) {
                return currentData + "-123";
            }
        });
        logger.info(zkClient.readData(node).toString());

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 订阅children变化
     *
     * @param zkClient
     * @param path
     * @author SHANHY
     * @create  2016年3月11日
     */
    public static void childChangesListener(ZkClient zkClient, final String path) {
        zkClient.subscribeChildChanges(path, new IZkChildListener() {

            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                logger.info("clildren of path " + parentPath + ":" + currentChilds);
            }

        });
    }

    /**
     * 订阅节点数据变化
     *
     * @param zkClient
     * @param path
     * @author SHANHY
     * @create  2016年3月11日
     */
    public static void dataChangesListener(ZkClient zkClient, final String path){
        zkClient.subscribeDataChanges(path, new IZkDataListener(){

            public void handleDataChange(String dataPath, Object data) throws Exception {
                logger.info("Data of " + dataPath + " has changed.");
            }

            public void handleDataDeleted(String dataPath) throws Exception {
                logger.info("Data of " + dataPath + " has changed.");
            }

        });
    }

    /**
     * 订阅状态变化
     *
     * @param zkClient
     * @author SHANHY
     * @create  2016年3月11日
     */
    public static void stateChangesListener(ZkClient zkClient){
        zkClient.subscribeStateChanges(new IZkStateListener() {

            public void handleStateChanged(KeeperState state) throws Exception {
                logger.info("handleStateChanged");
            }

            public void handleSessionEstablishmentError(Throwable error) throws Exception {
                logger.info("handleSessionEstablishmentError");
            }

            public void handleNewSession() throws Exception {
                logger.info("handleNewSession");
            }
        });
    }

}


