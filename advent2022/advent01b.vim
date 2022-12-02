let g:file = readfile('input.txt')
let g:sum =0 
let g:list = []

for i in g:file
  if len(i) == 0
    let g:list = g:list + [g:sum]
    let g:sum = 0
  else
    let g:sum = g:sum + i
  endif
endfor

let g:ans = reverse(sort(g:list, 'n'))
echo g:ans[0] + g:ans[1] + g:ans[2]
