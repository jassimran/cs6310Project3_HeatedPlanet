#bash

echo "Starting run at: $(date)"
while IFS=, read col1 col2 col3 col4 col5 col6 col7 col8 col9
do
	echo "Running: java scripts.SimulationDriver $col1 $col2 $col3 $col4 $col5 $col6 $col7 $col8 $col9"
    #java scripts.SimulationDriver $col1 $col2 $col3 $col4 $col5 $col6 $col7 $col8 $col9
done < simulations.csv
echo "Ending run at: $(date)"