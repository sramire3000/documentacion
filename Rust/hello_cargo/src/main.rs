use rand::Rng;

fn main() {
    let mut rng = rand::rng();
    let numero = rng.random_range(1..=100);

    println!("Hello, world! Tu numero aleatorio es: {numero}");
}
