object HelloWorld {
  def main(args: Array[String]): Unit = {
    var x = 419;
    var maxN = 72164;
    var list = scala.collection.mutable.ArrayBuffer.empty[Int];
    var scores =  scala.collection.mutable.ArrayBuffer.empty[Int];
    for (i <- 1 to x) {
     scores += 0;
    }
    var player =1;
    var pos = 0;
    list += 0;
    
    for (i <- 1 to maxN) {
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
}
