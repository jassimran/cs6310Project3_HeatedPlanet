package edu.gatech.cs6310.project2.team13.benchmark;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ Benchmark_Heap_Mediating.class, Benchmark_Heap_Pulling.class,
		Benchmark_Heap_Pushing.class })
public class AllHeapTests {

}
