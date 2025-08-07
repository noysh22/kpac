(module
  ;; Simple math functions for WASM testing
  
  ;; Export memory (optional, but useful for some tests)
  (memory (export "memory") 1)
  
  ;; Add function: takes two i32 parameters and returns their sum
  (func $add (param $a i32) (param $b i32) (result i32)
    local.get $a
    local.get $b
    i32.add)
  (export "add" (func $add))
  
  ;; Multiply function: takes two i32 parameters and returns their product
  (func $multiply (param $a i32) (param $b i32) (result i32)
    local.get $a
    local.get $b
    i32.mul)
  (export "multiply" (func $multiply))
  
  ;; Fibonacci function (recursive implementation)
  (func $fibonacci (param $n i32) (result i32)
    (local $temp i32)
    
    ;; Base cases: if n <= 1, return n
    local.get $n
    i32.const 1
    i32.le_s
    if (result i32)
      local.get $n
    else
      ;; Recursive case: fibonacci(n-1) + fibonacci(n-2)
      local.get $n
      i32.const 1
      i32.sub
      call $fibonacci
      
      local.get $n
      i32.const 2
      i32.sub
      call $fibonacci
      
      i32.add
    end)
  (export "fibonacci" (func $fibonacci))
  
  ;; Simple greeting function that returns a constant
  (func $get_answer (result i32)
    i32.const 42)
  (export "get_answer" (func $get_answer))
  
  ;; Power function: a^b (simple iterative implementation)
  (func $power (param $base i32) (param $exp i32) (result i32)
    (local $result i32)
    (local $counter i32)
    
    i32.const 1
    local.set $result
    
    i32.const 0
    local.set $counter
    
    ;; Loop: while counter < exp
    loop $power_loop
      local.get $counter
      local.get $exp
      i32.lt_s
      if
        ;; result = result * base
        local.get $result
        local.get $base
        i32.mul
        local.set $result
        
        ;; counter++
        local.get $counter
        i32.const 1
        i32.add
        local.set $counter
        
        ;; Continue loop
        br $power_loop
      end
    end
    
    local.get $result)
  (export "power" (func $power))
)
