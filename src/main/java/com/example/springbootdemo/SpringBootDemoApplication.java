package com.example.springbootdemo;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class SpringBootDemoApplication {

	private final CustomerRepository customerRepository;

    public SpringBootDemoApplication(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}
	@GetMapping
	public List<Customer> getCustomers(){
		return customerRepository.findAll();
	}

	record NewCustomerRequest(
			String name,
			String email,
			Integer age
	){

	}
	@PostMapping
	public void addCustomer(@RequestBody NewCustomerRequest request){
		Customer customer = new Customer();
		customer.setName(request.name);
		customer.setEmail(request.email);
		customer.setAge(request.age);
		customerRepository.save(customer);
	}
	@DeleteMapping("{customerId}")
	public void deleteCustomer(@PathVariable("customerId") Integer id){
		customerRepository.deleteById(id);
	}

	@PutMapping("{customerId}")
	public void updateCustomer(@PathVariable("customerId") Integer id){
		Customer existingCustomer = customerRepository.findById(id)
				.orElseThrow(()-> new EntityNotFoundException("Customer not found"));
		existingCustomer.setAge(existingCustomer.getAge());
		existingCustomer.setName(existingCustomer.getName());
		existingCustomer.setEmail(existingCustomer.getEmail());
		customerRepository.save(existingCustomer);

	}

}
