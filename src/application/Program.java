package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import model.entities.Sale;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Sale> sales = new ArrayList<>();
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			
			String line = br.readLine();
			
			while(line != null) {
				String[] fields = line.split(",");
				int month = Integer.parseInt(fields[0]);
				int year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				int items =	Integer.parseInt(fields[3]);
				double total = Double.parseDouble(fields[4]);
				sales.add(new Sale(month, year, seller, items, total));				
				line = br.readLine();
			}
			
			System.out.println("\nCinco primeiras vendas de 2016 de maior preço médio");
			
			Comparator<Sale> comp = (p1, p2) -> p1.averagePrice().compareTo(p2.averagePrice());
		
			List<Sale> higherAvgSale = sales.stream()
					.filter(s -> s.getYear() == 2016)
					.sorted(comp.reversed())
					.limit(5)
					.collect(Collectors.toList());
			
			higherAvgSale.forEach(System.out::println);
			
			String choosenSeller = "Logan";
			int monthA = 1;
			int monthB = 7;
			
			Predicate<Sale> p1 = a -> a.getSeller().contains(choosenSeller);
			Predicate<Sale> p2 = b -> b.getMonth() == monthA;
			Predicate<Sale> p3 = c -> c.getMonth() == monthB;
			
			double sum = sales.stream()
					.filter(p1.and(p2.or(p3)))
					.map(x -> x.getTotal())
					.reduce(0.0, (x, y) -> x + y);

			System.out.printf("%nValor total vendido pelo vendedor %s nos meses %d e %d = %.2f", choosenSeller, monthA, monthB, sum);
			
		}
		catch(IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		sc.close();
	}

}
