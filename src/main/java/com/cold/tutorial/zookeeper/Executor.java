package com.cold.tutorial.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author hui.liao
 *         2016/1/22 10:49
 */
public class Executor implements Watcher, Runnable, DataMonitorListener{

    private String fileName;
    private String[] exec;
    private ZooKeeper zk;
    private DataMonitor dm;
    private Process child;

    public Executor(String hostPort, String znode, String fileName, String exec[]) throws IOException {
        this.fileName = fileName;
        this.exec = exec;
        zk = new ZooKeeper(hostPort, 3000, this);
        dm = new DataMonitor(zk, znode, null, this);
    }

    public static void main(String[] args) throws IOException {
        String hostPort = "192.168.126.128";
        String znode = "/CGLibProxy";
        String filename = "d:/b.txt";
        String exec[] = new String[1];
        new Executor(hostPort, znode, filename, exec).run();
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while(!dm.dead) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        dm.process(event);
    }

    @Override
    public void exists(byte[] data) {
        if(data == null) {
            if (child != null) {
                System.out.println("Killing process");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            child = null;
        } else {
            if (child != null) {
                System.out.println("Stopping process");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(data);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Starting child");
                child = Runtime.getRuntime().exec(exec);
                new StreamWriter(child.getInputStream(), System.out);
                new StreamWriter(child.getErrorStream(), System.err);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closing(int rc) {
        synchronized (this) {
            notifyAll();
        }
    }

    static class StreamWriter extends Thread {

        OutputStream os;

        InputStream  is;

        StreamWriter(InputStream is, OutputStream os) {
            this.is = is;
            this.os = os;
            start();
        }

        public void run() {
            byte b[] = new byte[80];
            int rc;
            try {
                System.out.println("===========START TO WRITE===========");
                while ((rc = is.read(b)) > 0) {
                    os.write(b, 0, rc);
                }
                System.out.println("===========STOP TO WRITE===========");
            } catch (IOException e) {
            }

        }
    }
}
