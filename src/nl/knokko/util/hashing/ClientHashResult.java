package nl.knokko.util.hashing;

public class ClientHashResult {
	
	private final byte[] clientStartSeed;
	private final int[] serverStartSeed;
	
	private final int[] clientSessionSeed;
	private final int[] serverSessionSeed;
	
	private final byte[] testPayload;
	
	public ClientHashResult(byte[] clientStartSeed, int[] serverStartSeed, int[] clientSessionSeed, int[] serverSessionSeed, byte[] testPayload) {
		this.clientStartSeed = clientStartSeed;
		this.serverStartSeed = serverStartSeed;
		this.clientSessionSeed = clientSessionSeed;
		this.serverSessionSeed = serverSessionSeed;
		this.testPayload = testPayload;
	}
	
	public byte[] getClientStartSeed() {
		return clientStartSeed;
	}
	
	public int[] getServerStartSeed() {
		return serverStartSeed;
	}
	
	public int[] getClientSessionSeed() {
		return clientSessionSeed;
	}
	
	public int[] getServerSessionSeed() {
		return serverSessionSeed;
	}
	
	public byte[] getTestPayload() {
		return testPayload;
	}
}