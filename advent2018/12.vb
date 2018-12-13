Module mainModule 
Sub Main() 
'3
 Dim state As String = Console.ReadLine() 
 Console.ReadLine()
 state = "........................................" + state + "..........................................."
'7
 Dim growKey(31) As String
 Dim growVal(31) As String
'10
 For i = 0 To 31
  Dim line As String = Console.ReadLine() 
  Dim words As String() = line.Split("=>")
  growKey(i) = words(0).Trim()
  growVal(i) = words(1).Trim()
 Next
'17
 For i = 0 To 19
  Console.WriteLine(state.Length)
  Console.WriteLine(state)
  Dim something = ""
  For c = 0 To state.Length - 6
   Dim subb = state.Substring(c, 5)
   Dim found = 0
    For key = 0 To 31
     If growKey(key) = subb Then
      something = something + growVal(key)
      found = 1
     End If
    Next
   If found = 0 Then
    Console.WriteLine("not found")
    Console.WriteLine(subb)

   End If
  Next
  state = ".." + something + ".."
 Next
 Dim score = 0
 Console.WriteLine(state)
 For i = -40 To state.Length - 41
  Dim v = state.Substring(0,1)
  state = state.Substring(1)
  If v = "#" Then
   score = score + i
  End If
 Next
 Console.WriteLine(score) 
End Sub
End Module  
