let g:file = readfile('input.txt')
let g:sum =0 
let g:max = 0
for i in g:file
  if len(i) == 0
    if g:max < g:sum
      let g:max = g:sum
    endif
    let g:sum = 0
  else
    let g:sum = g:sum + i
  endif
endfor

echo g:max
