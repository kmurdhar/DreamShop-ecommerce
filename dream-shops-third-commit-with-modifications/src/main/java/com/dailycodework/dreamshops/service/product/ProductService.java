package com.dailycodework.dreamshops.service.product;

import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.exceptions.AlreadyExistsException;
import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.*;
import com.dailycodework.dreamshops.repository.*;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.ProductUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final VehicleRepository vehicleRepository;
	private final ModelMapper modelMapper;
	private final ImageRepository imageRepository;
	private final CartItemRepository cartItemRepository;
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;

	@Override
	public Product addProduct(AddProductRequest request) {
		Category category = Optional.ofNullable(
				categoryRepository.findByNameAndSubCategoryNameAndEndCategoryName(request.getCategory().getName(),
						request.getCategory().getSubCategoryName(), request.getCategory().getEndCategoryName()))
				.orElseGet(() -> {
					Category newCategory = new Category(request.getCategory().getName(),
							request.getCategory().getSubCategoryName(), request.getCategory().getEndCategoryName());
					return categoryRepository.save(newCategory);
				});
		Vehicle vehicle = Optional
				.ofNullable(vehicleRepository.findByNameAndVehicleBrandAndVehicleModel(request.getVehicle().getName(),
						request.getVehicle().getVehicleBrand(), request.getVehicle().getVehicleModel()))
				.orElseGet(() -> {
					Vehicle newVehicle = new Vehicle(request.getVehicle().getName(),
							request.getVehicle().getVehicleBrand(), request.getVehicle().getVehicleModel());
					return vehicleRepository.save(newVehicle);
				});

		if (productExists(request, category, vehicle)) {
			throw new AlreadyExistsException(request.getName() + " " + request.getVehicle().getName()
					+ " already exists, you may update this product instead!");
		}

		request.setCategory(category);
		request.setVehicle(vehicle);
		return productRepository.save(createProduct(request, category));
	}

	private boolean productExists(AddProductRequest request, Category category, Vehicle vehicle) {
		List<Product> products = productRepository.checkProductExist(category.getId(), vehicle.getId());
		return products.stream().filter(product -> product.getBrand().equalsIgnoreCase(request.getBrand())
				&& product.getName().equalsIgnoreCase(request.getName())).count() > 0;

	}

	private Product createProduct(AddProductRequest request, Category category) {
		return new Product(request.getName(), request.getVehicle(), request.getPrice(), request.getInventory(),
				request.getDescription(), category, request.getBrand());
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
	}

	@Override
	public void deleteProductById(Long id) {
		List<CartItem> cartItems = cartItemRepository.findByProductId(id);
		List<OrderItem> orderItems = orderItemRepository.findByProductId(id);
		List<Image> imageItems = imageRepository.findByProductId(id);

		productRepository.findById(id).ifPresentOrElse(product -> {
			// Functional approach for category removal
			/*
			 * Optional.ofNullable(product.getCategory()) .ifPresent(category ->
			 * category.getProducts().remove(product));
			 */ product.setCategory(null);

			/*
			 * Optional.ofNullable(product.getVehicle()) .ifPresent(vehicle ->
			 * vehicle.getProducts().remove(product));
			 */ product.setVehicle(null);

			// Functional approach for updating cart items
			cartItems.stream().peek(cartItem -> cartItem.setProduct(null)).peek(CartItem::setTotalPrice)
					.forEach(cartItemRepository::save);

			// Functional approach for updating order items
			orderItems.stream().peek(orderItem -> orderItem.setProduct(null)).forEach(orderItemRepository::save);

			imageItems.stream().peek(imageItem -> imageItem.setProduct(null)).forEach(imageRepository::save);

			productRepository.delete(product);
		}, () -> {
			throw new EntityNotFoundException("Product not found!");
		});
	}

	@Override
	public Product updateProduct(ProductUpdateRequest request, Long productId) {
		return productRepository.findById(productId)
				.map(existingProduct -> updateExistingProduct(existingProduct, request)).map(productRepository::save)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
	}

	private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
		existingProduct.setName(request.getName());

		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		existingProduct.setDescription(request.getDescription());

		Category category = new Category(request.getCategory().getName(), request.getCategory().getSubCategoryName(),
				request.getCategory().getEndCategoryName());
		existingProduct.setCategory(category);

		Vehicle vehicle = new Vehicle(request.getVehicle().getName(), request.getVehicle().getVehicleBrand(),
				request.getVehicle().getVehicleModel());
		existingProduct.setVehicle(vehicle);
		return existingProduct;

	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	/*
	 * @Override public List<Product> getProductsByEndCategoryName(String
	 * endCategoryName) { return
	 * categoryRepository.findByEndCategoryName(endCategoryName); }
	 * 
	 * @Override public List<Product> getProductsByBrand(String brand) { return
	 * productRepository.findByBrand(brand); }
	 * 
	 * @Override public List<Product> getProductsByCategoryAndBrand(String category,
	 * String brand) { return productRepository.findByCategoryNameAndBrand(category,
	 * brand); }
	 */

	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		return productRepository.findByBrandAndName(brand, name);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return null;// productRepository.countByBrandAndName(brand, name);
	}

	@Override
	public List<ProductDto> getConvertedProducts(List<Product> products) {
		return products.stream().map(this::convertToDto).toList();
	}

	@Override
	public ProductDto convertToDto(Product product) {
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
		List<Image> images = imageRepository.findByProductId(product.getId());
		List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
		productDto.setImages(imageDtos);
		return productDto;
	}

	@Override
	public List<Product> findDistinctProductsByName() {
		List<Product> products = productRepository.findAll();
		Map<String, Product> distinctProductsMap = products.stream()
				.collect(Collectors.toMap(Product::getName, product -> product, (existing, replacement) -> existing));
		return new ArrayList<>(distinctProductsMap.values());
	}

	@Override
	public List<Product> getProductsByMainCategory(String category) {
	List<Category> categories =	categoryRepository.findByName(category);	
	List<Long> categoriesId =
			categories.stream()
		                .map(objects -> objects.getId())
		                .collect(Collectors.toList());	
	 
				return productRepository.findAllByCategoryId(categoriesId);
	}

	@Override
	public List<Product> getProductsBySubCategory(String category) {
		List<Category> categories =	categoryRepository.findBySubCategoryName(category);	
		List<Long> categoriesId =
				categories.stream()
			                .map(objects -> objects.getId())
			                .collect(Collectors.toList());	
		
	 
		return productRepository.findAllByCategoryId(categoriesId);
		}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllDistinctBrands() {
		return productRepository.findAll().stream().map(Product::getBrand).distinct().collect(Collectors.toList());
	}

	@Override
	public List<Product> findProductsByVehicleModel(String vehicle) {
		List<Vehicle> vehicles =	vehicleRepository.findByVehicleModel(vehicle);	
		List<Long> vehiclesId =
				vehicles.stream()
			                .map(objects -> objects.getId())
			                .collect(Collectors.toList());	
		return productRepository.findAllByVehicleId(vehiclesId);
	}

	@Override
	public List<Product> findProductsByVehicleBrand(String vehicle) {

		List<Vehicle> vehicles =	vehicleRepository.findByVehicleBrand(vehicle);	
		List<Long> vehiclesId =
				vehicles.stream()
			                .map(objects -> objects.getId())
			                .collect(Collectors.toList());	
		return productRepository.findAllByVehicleId(vehiclesId);
	
	}

	@Override
	public List<Product> findProductsAllVehicle(String vehicle) {
		List<Vehicle> vehicles =	vehicleRepository.findByName(vehicle);	
		List<Long> vehiclesId =
				vehicles.stream()
			                .map(objects -> objects.getId())
			                .collect(Collectors.toList());	
		return productRepository.findAllByVehicleId(vehiclesId);
	}

}
