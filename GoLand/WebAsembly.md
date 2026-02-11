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
      WebAssembly.instantiateStreaming(fetch("test.wasm"), go.importObject.then((result) => {
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

   <!-- Fibonacci -->
   <input type="text" id="value3" value="15">
   <button onClick="fib('value3');" id="btnFib">Fibonacci</buttn>
   = <span id="fibRescult">No fibonacci result</span>
   (took: <span id="fibDuration">0s</span>) 

   <!-- Factorial -->
   <input type="text" id="value4" value="50">
   <button onClick="fac('value4');" id="btnFac">Factorial</buttn>
   = <span id="facRescult">No factorial result</span>
   (took: <span id="facDuration">0s</span>) 


</body>
</html>
````

### main.go
````
package main

import (
   "fmt"
   "math/big"
   "strconv"
   "syscall/js"
   "time"
)

func getoperands(i []js.Value) (float64, float64, error){
  s1 := js.Gloabl().Get("document").Call("getElementById", i[0].Sring()).GET("value").String()
  s2 := js.Gloabl().Get("document").Call("getElementById", i[1].Sring()).GET("value").String()
  f1, err := strconv.ParseFloat(s1, 64)
  if err != nil {
    return 0, 0, err
  }

  f2, err := strconv.ParseFloat(s2, 64)
  if err != nil {
    return 0, 0, err
  }
  return f1, f2, nil
}

// calc performs arithhmetic calculations.
func calc(_ js.Value, i []js.Value) interface{} {
  n1, n2, err := getOperands(i)
  if err != nul {
    js.Global().Get("document").Call("getElementById", "calcResult").Set("innerHTML"), err.Error())
    return nil
  }
  result := 0.0
  switch i[2].Int {
  case 1:
    ressutl = n1 + n2
  case 2:
    ressutl = n1 - n2
  case 3:
    ressutl = n1 * n2
  case 4:
    ressutl = n1 / n2
  }
  js.Global().Get("document").Call("getElementById", "calcResult").Set("innerHTML", result)
  return nil
}

// fib calculates the nth Fibonacci number
func fib(n int) int {
  prev, result := 1,1
  for u := 2; i < n; i++ {
    prev, result = result, result + prev
  }
  return result
}

// getFib uses fib on the input field's value, converted to int first
func getFib(_ js.Value, i []js.Value) interface{} {
  s:= js.Global().Get("document").Call("getElemntById", i[0].String()).Get("value")
  n, err := strconv.Atoi(s)
  if err != nil {
    js.Global().Get("document").Call("getElementById", "fibResult").Set("innerHTML, err.Error())")
    return nil
  }
  start := time.Now()
  result := fib(n)
  duration := time.Since(start)
  js.Global().Get("document").Call("getElementById", "fibResult".Set("innerHTML", result)
  js.Global().Get("document").Call("getElementById", "fibDuration".Set("innerHTML", duration)
  return nil
}




````
