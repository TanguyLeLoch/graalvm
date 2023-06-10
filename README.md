Certainly! Here's the README in Markdown format:

```markdown
# GraalVM Test

This project aims to utilize GraalVM to run Java code natively on the host machine. It also serves as a platform for enhancing knowledge and proficiency in hexagonal architecture and Domain-Driven Design (DDD) principles.

## Features

- **GraalVM Integration:** Explore the capabilities of GraalVM for native execution of Java code.
- **Hexagonal Architecture:** Implement the application using a hexagonal architecture pattern to achieve modularity and separation of concerns.
- **Domain-Driven Design (DDD):** Apply DDD principles to design the core domain model and create a rich, expressive business logic layer.

## Prerequisites

- [Java Development Kit (JDK) 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [GraalVM](https://www.graalvm.org/downloads/) (latest version)
- [Apache Maven](https://maven.apache.org/download.cgi) (latest version)
- [mongoDb](https://www.mongodb.com/) running on default port 5432

## Getting Started

Follow the instructions below to set up the project locally on your machine.

1. Clone the repository:

   ```bash
   git clone https://github.com/TanguyLeLoch/graalvm.git
   ```

2. Set up the GraalVM environment by following the installation instructions provided by GraalVM.

3. Build the project using Maven:

   ```bash
   cd graalvm
   mvn -Pnative native:compile
   ```

## Usage

Once the project is successfully built, you can run it as follows:

```bash
./target/graalvm
```

This command will execute the application using GraalVM, leveraging the native compilation capabilities.

## Contributing

Contributions are welcome! If you want to contribute to this project, please follow these steps:

1. Fork this repository.
2. Create a new branch for your feature or bug fix.
3. Make the necessary changes and additions.
4. Commit and push your changes to your forked repository.
5. Submit a pull request, clearly describing the changes you've made.

Please ensure that your contributions adhere to the existing coding style and include appropriate tests and documentation.

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgements

We would like to express our gratitude to the following resources and projects that have inspired and influenced this work:

- [GraalVM Documentation](https://www.graalvm.org/documentation/)
- [Hexagonal Architecture by Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Domain-Driven Design by Eric Evans](https://domainlanguage.com/ddd/)
- [Open Source Frameworks and Libraries Used (List them here)]

## Contact

For any questions or feedback, please feel free to reach out to lelochtanguy@gmail.com.
```
