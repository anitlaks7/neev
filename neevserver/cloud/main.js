
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("summationInventory", function(request, response) {
	var query = new Parse.Query("NeevRawMaterialItem");
	query.find({
    success: function(results) {
      var sum = 0;
      for (var i = 0; i < results.length; ++i) {
        sum += results[i].get("Total");
      }
      response.success(sum);
    },
    error: function() {
      response.error("Summation on NeevRawMaterialItem failed");
  response.success("Summation on NeevRawMaterialItem Successful");
  }
});
});