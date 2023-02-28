const cluster = require('cluster');
const numCPUs = require('os').cpus().length;

if (cluster.isMaster) {
  console.log(`Master process is running with pid ${process.pid}`);

  // Fork workers
  for (let i = 0; i < numCPUs; i++) {
    cluster.fork();
  }

  cluster.on('exit', (worker, code, signal) => {
    console.log(`Worker ${worker.process.pid} died with code ${code} and signal ${signal}`);
    console.log('Starting a new worker');
    cluster.fork();
  });
} else {
  console.log(`Worker process is running with pid ${process.pid}`);

  // Perform calculations and DNA operations
  const a = 10;
  const b = 20;
  const sum = a + b;
  console.log(`Sum of ${a} and ${b} is ${sum}`);

  const dna = 'ATCG';
  const complement = dna.split('').map(base => {
    switch(base) {
      case 'A':
        return 'T';
      case 'T':
        return 'A';
      case 'C':
        return 'G';
      case 'G':
        return 'C';
      default:
        return base;
    }
  }).join('');
  console.log(`Complement of ${dna} is ${complement}`);

  // Send result back to master process
  process.send({ sum, complement });

  // Exit worker process
  process.exit();
}
