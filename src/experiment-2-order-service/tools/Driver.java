import java.util.*;
public class Driver {
  public static void main(String[] a){
    Random r=new Random(42); OrderService s=new OrderService();
    String[] cust={"  ana ","Ardit","x"," Bob  "}; String[] tiers={"GOLD","SILVER","BASIC"}; String[] cats={"FOOD","BOOK","OTHER"};
    for(int i=0;i<20000;i++){
      List<OrderService.Item> it=new ArrayList<>(); int n=r.nextInt(5);
      for(int j=0;j<n;j++){
        double p = r.nextInt(5)==0 ? -(r.nextInt(5000)/100.0) : (r.nextInt(2)==0? r.nextInt(100000)/100.0 : r.nextDouble()*500);
        it.add(new OrderService.Item("i"+j,p,r.nextInt(16),cats[r.nextInt(3)]));
      }
      System.out.println(s.buildInvoice(cust[r.nextInt(4)],tiers[r.nextInt(3)],it));
      System.out.println(s.buildShippingLabel(cust[r.nextInt(4)]," city ","xk"));
    }
    List<String> nm=s.names(List.of(new OrderService.Item("a",1,1,"FOOD")));
    try{ nm.add("z"); System.out.println("names mutable"); }catch(Exception e){ System.out.println("names IMMUTABLE "+e.getClass().getSimpleName()); }
  }
}
