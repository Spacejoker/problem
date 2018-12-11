object Main extends App {
  var x = 419;
  var maxN = 7216400;
  var list = scala.collection.mutable.ArrayBuffer.empty[Long];
  var scores =  scala.collection.mutable.ArrayBuffer.empty[Long];
  for (i <- 1 to x) {
   scores += 0;
  }
  var player =1;
  var pos = 0;
  list += 0;

  for (i <- 1 to maxN) {
    if (i%10000 == 0) println(i)
    player = player + 1
    player %= x
    if (i % 23 != 0) {
      pos += 2;
      pos %= list.size
      list.insertAll(pos+1, List(i));
    } else {
      pos = (pos - 6 + list.size)%list.size
      scores.update(player, scores.apply(player) + i + list.apply(pos));
      list.remove(pos);
      pos = pos - 1
    }
  }
  println(scores.max)
}
