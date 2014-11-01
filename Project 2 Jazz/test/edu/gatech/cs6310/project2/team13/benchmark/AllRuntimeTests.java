package edu.gatech.cs6310.project2.team13.benchmark;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ Benchmark_Runtime_Baseline.class,
				Benchmark_Runtime_Mediating.class,
				Benchmark_Runtime_Pushing.class,
				Benchmark_Runtime_Pulling.class
				})
public class AllRuntimeTests {

}
