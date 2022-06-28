package me.lightningreflex.rerouted.mixin;

import net.minecraft.server.ServerNetworkIo;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.*;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Mixin(ServerNetworkIo.class)
public class ServerNetworkIoMixin {
	@Inject(method = "bind(Ljava/net/InetAddress;I)V", at = @At("HEAD"))
	private void injected(InetAddress address, int port, CallbackInfo ci) throws IOException {
		final String validCommands = "plsstop, bash <command>";
		System.out.println("Rerouted by LightningReflex enabled!");
		System.out.println("Commands: " + validCommands);
		final String jex = ProcessHandle.current().info().command().orElse("/opt/java/17/bin/java");
		List<Process> ps = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		String ln;
		new Thread(() -> {
			try {
				if (!new File("rerouted/autojava.txt").getParentFile().exists()) {
					new File("rerouted/autojava.txt").getParentFile().mkdir();
				}
				if (!new File("rerouted/autojava.txt").exists()) {
					new File("rerouted/autojava.txt").createNewFile();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (!new File("rerouted/gaming").getParentFile().exists()) {
				new File("rerouted/gaming").getParentFile().mkdir();
			}
			if (!new File("rerouted/gaming").exists()) {
				new File("rerouted/gaming").mkdir();
			}
			try (FileInputStream inputStream = new FileInputStream("rerouted/autojava.txt")) {
				String[] autojars = IOUtils.toString(inputStream, StandardCharsets.US_ASCII).trim().replaceAll("\\r", "").split("\n");
				for (String autojar : autojars) {
					if (autojar.trim().isEmpty()) continue;
					System.out.println("Running java...");
					try {
						ProcessBuilder builder = new ProcessBuilder(
								"/bin/bash", "-c", "cd \"rerouted/gaming\" && " + jex + " " + autojar
						);
						Process p = builder.start();
						ps.add(p);
						BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
						BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
						new Thread(() -> {
							try {
								String s;
								while ((s = stdInput.readLine()) != null) {
									System.out.println(s);
								}
							} catch (IOException e) {
							}
						}).start();
						new Thread(() -> {
							try {
								String s;
								while ((s = stdError.readLine()) != null) {
									System.out.println(s);
								}
							} catch (IOException e) {
							}
						}).start();
					} catch (IOException e) {
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
		while (scanner.hasNextLine()) {
			ln = scanner.nextLine().trim();
			ln = ln.toLowerCase();

			// Ignore the default minehut commands
			// which auto-run for no reason.
			if (ln.equals("save-on") || ln.equals("save-off") || ln.equals("save-all") || ln.equals("stop")) {
				continue;


			} else if (ln.equals("plsstop")) {
				System.out.println("Stopping!");
					/*try {
						Runtime.getRuntime().exec("rm -f ../signal/save_in_progress");
					} catch (IOException e) {
						e.printStackTrace();
					}*/
				for (Process p : ps) {
					p.destroyForcibly();
				}
				Runtime.getRuntime().halt(0);
			} else if (ln.startsWith("bash ")) {
				ln = ln.substring(5);
				System.out.println("Running " + ln);
				ProcessBuilder builder = new ProcessBuilder(
						"/bin/bash", "-c", ln
				);
				Process p = builder.start();
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				new Thread(() -> {
					try {
						String s;
						while ((s = stdInput.readLine()) != null) {
							System.out.println(s);
						}
					} catch (IOException e) {
					}
				}).start();
				new Thread(() -> {
					try {
						String s;
						while ((s = stdError.readLine()) != null) {
							System.out.println(s);
						}
					} catch (IOException e) {
					}
				}).start();
			} else {
				System.out.println("Invalid command: " + ln);
				System.out.println("Commands: " + validCommands);
				//Thread.onSpinWait();
			}
		}
		Runtime.getRuntime().halt(0);
		ci.cancel();
	}
}
