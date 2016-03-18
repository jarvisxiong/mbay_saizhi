package org.sz.mbay.dfs.fastdfs;

import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.fs.fastdfs.FDFSService;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	
	private static FSClient fsClient = FSClientFactory
			.getClient(ClientType.FDFS);
	private static FDFSService fdfsService = (FDFSService) fsClient
			.getFsSerice();
			
	public void testUpload() {
//		try {
//			System.out.println(fdfsService.uploadFile("c:/bdm_uninstall.log"));
//		} catch (Exception e) {
//		}
		
		// System.out.println(fdfsService.getStorageClient("group1").getStorageServer().getInetSocketAddress());
		// System.out.println(fdfsService.getStorageClient("group2").getStorageServer().getInetSocketAddress());
		// System.out.println(fdfsService.getStorageClient().getStorageServer().getInetSocketAddress());
		// System.out.println(fdfsService.getStorageClient().getStorageServer().getInetSocketAddress());
		// System.out.println(fdfsService.getStorageClient("group2").getStorageServer().getInetSocketAddress());
		// System.out.println(fdfsService.getStorageClient("group2").getStorageServer().getInetSocketAddress());
		
		//System.out.println(fdfsService.getFullPath("group2/M00/00/19/wKioTVX5NK2AJc5AAAAIcgEaXxw689.log"));
		
		System.out.println(fdfsService.getFullPath(""));
		System.out.println(fdfsService.getFullPath("group2/M00/00/19/wKioTVX5NK2AJc5AAAAIcgEaXxw689.log"));
	}
	
	public void testListTrackerServer() {
		// try {
		// TrackerServer server = fdfsService.getTrackerServer();
		// System.out.println(server.getInetSocketAddress());
		//
		// TrackerServer server2 = fdfsService.getTrackerServer(0);
		// System.out.println(server2.getInetSocketAddress());
		//
		// TrackerServer server3 = fdfsService
		// .getTrackerServer("192.168.128.129");
		// System.out.println(server3.getInetSocketAddress());
		// } catch (Exception e) {
		// }
	}
	
	public void testListStorageServers() {
		// try {
		// StorageServer[] storageServers = fdfsService
		// .getStorageServers("group1");
		// if (storageServers != null) {
		// for (int k = 0; k < storageServers.length; k++) {
		// System.out.println(k + 1 + ". " +
		// storageServers[k].getInetSocketAddress()
		// .getAddress().getHostAddress()
		// + ":" + storageServers[k].getInetSocketAddress()
		// .getPort());
		// }
		// }
		// } catch (Exception e) {
		// }
	}
	
	public void testListStorageServer() {
		// try {
		// StorageServer storageServer = fdfsService
		// .getStorageServer("group1");
		// if (storageServer != null) {
		// System.out.println(
		// storageServer.getInetSocketAddress().getAddress()
		// .getHostAddress()
		// + ":" + storageServer.getInetSocketAddress()
		// .getPort());
		// }
		// } catch (Exception e) {
		// }
	}
	
	public void testStorageClient() {
		// try {
		// EnhancedStorageClient client = fdfsService
		// .getStorageClient("group1");
		// System.out.println(client.getStorageServer());
		// } catch (Exception e) {
		// }
	}
	
	public void testDownload() {
		try {
			// byte[] data = fsClient.downloadFile(
			// "group1/M00/00/00/wKioTFTRwUqAGCqRAA1rIuRd3Es999.jpg");
			// System.out.println(data.length);
		} catch (Exception e) {
		}
	}
	
	public void testDelete() {
		try {
			// fsClient.deleteFile(
			// "group1/M00/00/00/wKioTFTRwUqAGCqRAA1rIuRd3Es999.jpg");
		} catch (Exception e) {
		}
	}
	
	public void testGetFileInfo() {
		try {
			// FDFSFileInfo info = (FDFSFileInfo) fsClient.getFileInfo(
			// "group2/M00/00/07/wKioTVU3P4eAJG_iAAAZN00Sfh0992.png");
			// System.out.println(info);
		} catch (Exception e) {
		}
	}
	
	public void testGetOneTracker() {
		// String trackerHost = ((FDFSService) fsClient.getFsSerice())
		// .getTrackerServer().getInetSocketAddress().getHostString();
		// String pubIp = EnhancedTrackerGroup.getPublicNetworkIp(trackerHost);
		// String host = pubIp != null ? pubIp : trackerHost + ":" +
		// +ClientGlobal.getG_tracker_http_port();
		// System.out.println(host);
	}
}
