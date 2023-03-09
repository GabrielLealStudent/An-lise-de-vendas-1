package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) throws ParseException {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Sale> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}

			List<Double> five = list.stream().filter(x -> x.getYear() < 2017).map(x -> x.averagePrice())
					.sorted(Comparator.reverseOrder()).limit(5).collect(Collectors.toList());

			List<Sale> filteredList = list.stream().filter(x -> x.getYear() == 2016)
					.sorted(Comparator.comparing(Sale::averagePrice).reversed()).limit(5).collect(Collectors.toList());

			double logan = list.stream().filter(x -> "Logan".equals(x.getSeller()))
					.filter(x -> x.getMonth() == 1 || x.getMonth() == 7).mapToDouble(x -> x.getTotal()).sum();

			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio ");
			System.out.println();

			int counter = 0;
			for (Sale sale : filteredList) {
				System.out.println(sale.toString() + String.format("%.2f", five.get(counter)));
				counter++;
			}
			System.out.println();
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + logan);

		} catch (IOException e) {
			System.out.println(
					"Erro: " + path +" (O sistema não pode encontrar o arquivo especificado) ");
		}

		sc.close();

	}

}
