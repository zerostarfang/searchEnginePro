package com.zerofang.pagerank.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.regex.Pattern;

import scala.Tuple2;

import com.google.common.collect.Iterables;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;

public final class JavaPageRank {
	private static final Pattern SPACES = Pattern.compile("\\s+");

	static void showWarning() {
		String warning = "错误";
		System.err.println(warning);
	}

	private static class Sum implements Function2<Double, Double, Double> {
		@Override
		public Double call(Double a, Double b) {
			return a + b;
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err
					.println("Usage: JavaPageRank <file> <number_of_iterations>");
			System.exit(1);
		}

		showWarning();

		SparkConf sparkConf = new SparkConf().setAppName("JavaPageRank");
		JavaSparkContext ctx = new JavaSparkContext(sparkConf);

		JavaRDD<String> lines = ctx.textFile(args[0], 1);

		JavaPairRDD<String, Iterable<String>> links = lines
				.mapToPair(new PairFunction<String, String, String>() {
					@Override
					public Tuple2<String, String> call(String s) {
						String[] parts = SPACES.split(s);
						return new Tuple2<String, String>(parts[0], parts[1]);
					}
				}).distinct().groupByKey().cache();
		JavaPairRDD<String, Double> ranks = links
				.mapValues(new Function<Iterable<String>, Double>() {
					@Override
					public Double call(Iterable<String> rs) {
						return 1.0;
					}
				});

		for (int current = 0; current < Integer.parseInt(args[1]); current++) {
			JavaPairRDD<String, Double> contribs = links
					.join(ranks)
					.values()
					.flatMapToPair(
							new PairFlatMapFunction<Tuple2<Iterable<String>, Double>, String, Double>() {
								@Override
								public Iterator<Tuple2<String, Double>> call(
										Tuple2<Iterable<String>, Double> s) {
									int urlCount = Iterables.size(s._1);
									List<Tuple2<String, Double>> results = new ArrayList<>();
									for (String n : s._1) {
										results.add(new Tuple2<>(n, s._2()
												/ urlCount));
									}
									return results.iterator();
								}
							});

			ranks = contribs.reduceByKey(new Sum()).mapValues(
					new Function<Double, Double>() {
						@Override
						public Double call(Double sum) {
							return 0.15 + sum * 0.85;
						}
					});
		}

		List<Tuple2<String, Double>> output = ranks.collect();
		for (Tuple2<?, ?> tuple : output) {
			System.out.println(tuple._1() + " has rank: " + tuple._2() + ".");
		}

		ctx.stop();
	}
}