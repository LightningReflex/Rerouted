<h1 align="center" id="title">Rerouted</h1>

<p id="description">Fabric mod I made for Minehut to run any server jar file you would need. I was bored. Don't blame me man D: (Some credit goes to https://github.com/ayunami2000/)</p>

<p align="center"><img src="https://img.shields.io/discord/992429435687018588?label=Discord" alt="shields"> <img src="https://img.shields.io/github/license/LightningReflex/Rerouted" alt="shields"></p>
<p>&nbsp;</p>

<h2>🚀 Demo</h2>

[https://streamable.com/w0rbj0](https://streamable.com/w0rbj0)
<p>&nbsp;</p>

<h2>✈ Features</h2>

Here're some of the features:

*   Run any jar file you want
<p>&nbsp;</p>

<h2>🛠️ Installation Steps:</h2>

<p>1. Build the jar.</p>

```
./gradlew build
```

<p>2. Start the server.</p>

<p>3. Go to rerouted/autojava.txt and enter the java startup arguments. Ex:</p>

```
-Xmx1024M -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200 -XX:+UnlockExperimentalVMOptions -XX:+DisableExplicitGC -XX:+AlwaysPreTouch -XX:G1NewSizePercent=30 -XX:G1MaxNewSizePercent=40 -XX:G1HeapRegionSize=8M -XX:G1ReservePercent=20 -XX:G1HeapWastePercent=5 -XX:G1MixedGCCountTarget=4 -XX:InitiatingHeapOccupancyPercent=15 -XX:G1MixedGCLiveThresholdPercent=90 -XX:G1RSetUpdatingPauseTimePercent=5 -XX:SurvivorRatio=32 -XX:+PerfDisableSharedMem -XX:MaxTenuringThreshold=1 -DIReallyKnowWhatIAmDoingISwear -Djline.terminal=jline.UnsupportedTerminal -Dlog4j2.formatMsgNoLookups=true -jar yourJarName.jar
```

<p>4. Put your desired jar file in rerouted/gaming.</p>

<p>5. Type this 2-4 times in the console until the server stops.</p>

```
plsstop
```
<p>6. Start the server and it will run the file in rerouted/gaming and make that it's working directory.</p>
<p>&nbsp;</p>


<h2>💖 Like my work?</h2>

If you want to support me contact me here<p>https://discord.gg/C6RQMtXXcV</p>