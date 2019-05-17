In folders of the form Temp<temperature><strategy>:
	*<temperature> takes values [5,19,15,50], which correspond to the temperature parameter of Simulated Annealing (SA).
	*<strategy> takes values which refer to the strategy the algorithm used to move heuristics in the feature space:
		*"MoveCloser":  huristics move randomly towards the state they best solve when selected by the SA mechanism.
		*MoveCloserRand_5": The same as the previous one but here we also randomize heuristics that did not were chosen by the selection mechanism of SA.

Inside folders .csv files are labeled as follows:
	*features.csv: Features of the initial state of instances in the training set.
	*heuristics_Test.csv: Average waste of applying all the heuristics to every full instance in the test set.
	*heuristics_Train.csv: The same as the previous one but for the train set.

	*<method>_<set>_<epochs>_<seed>.csv:
		*<method>: takes values:
			*"Hyp": Hyperheuristic learned with SA was used.
			*"rand": Random hyperheuristic (selects randomly a heuristic with non uniform probability, determined by initial state of SA).
		*<set>:
			*"Train": Training set Average waste results for each instance.
			*"Test": Test set Average waste results for each instance.
		*<epochs>: Takes values [1,5,10,25,50,100]: times SA sees each instance of the training set.
		*<seed>: random seed used. All seeds with same <method>,<set> and <epochs> should be averaged.

	*<method>CondMatrix<set>_<epochs>_<seed>.csv: Contains the hyperheuristic matrix used to decide the heuristic to apply at each state of instances.
	These matrices also contain the times each heuristic was chosen for the corresponding set.

