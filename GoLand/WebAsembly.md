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
   </script">
</body>

</html>
````
