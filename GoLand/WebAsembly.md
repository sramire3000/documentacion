# Ejemplo del GO con Web Asembly


### index.html
````
<html>

<head>
   <meta charset="utf-8">
   <title>Go wasm</title>
</head>

<body>
   <script src="wasm_exec.js"></script">
   <!-- Go WASM initialization -->
   <script">
      if(!WebAssembly.instantiateStreaming){
        WebAssemby.instantiateStreaming = async() => {
           const source = await (await resp).arrayBuffer();
           return await WebAssembly.instantiate(source, importObject);
        };
      }

      const go = new Go();
      let mod, inst;
      WebAssembly.instantiateStreaming(fetch("test.wasm"), go.importObject.then((
         mod = result.module;
         inst = result.instance;
         document.getElemenById("runButton").disabled = false;
      }).catch((err) => {
         console.error(err);
      );

      async function run(){
        document.getElementById("runButton").disabled = true;
        console.clear;
        await go.run(inst);
        inst = await WebAssembly.instantiate(mod, go.importObject);
      }
   </script">

   <button onClick="run();" id="runButton" disabled>Run</button>
   <ht>

   <div id="body" style="opacity: 0">

   <!-- Calculator -->
   <input type="text" id="value1" value="10">
   <input type="text" id="value2" value="20">
   <button onClick="calc('value1', 'value2', 1);" id="btnAdd">Add</buttn>
   <button onClick="calc('value1', 'value2', 2);" id="btnSub">Sibtract</buttn>
   <button onClick="calc('value1', 'value2', 3);" id="btnMul">Multiply</buttn>
   <button onClick="calc('value1', 'value2', 4);" id="btnDiv">Divide</buttn>
   = <span id="calcRescult">No calculator result</span>





</body>

````
</html>
