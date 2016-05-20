/// #Generic read in
///	##Args
///	1. String to show user before asking for input
///	##Output
///	1. Generic type (which implements FromStr trait)
fn get_in<T: std::str::FromStr>(said: &str) -> T {
	loop {
		println!("{}", said);

		let mut instr = String::new();

		io::stdin().read_line(&mut instr)
			.expect("failed to read line");

		return match instr.trim().parse() {
					Ok(num) => num,
					Err(_) =>{
						println!("Incorrect format");
						continue;
					} 
				};

	}
}