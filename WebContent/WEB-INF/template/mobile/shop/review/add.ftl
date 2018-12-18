<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="PTNETWORK Team">
	<meta name="copyright" content="PTNETWORK">
	<title>${product.name} ${message("shop.review.title")}[#if showPowered] - Powered By PTNETWORK[/#if]</title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/mobile/shop/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/mobile/shop/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/mobile/shop/css/animate.css" rel="stylesheet">
	<link href="${base}/resources/mobile/shop/css/common.css" rel="stylesheet">
	<link href="${base}/resources/mobile/shop/css/review.css" rel="stylesheet">
	<!--[if lt IE 9]>
		<script src="${base}/resources/mobile/shop/js/html5shiv.js"></script>
		<script src="${base}/resources/mobile/shop/js/respond.js"></script>
	<![endif]-->
	<script src="${base}/resources/mobile/shop/js/jquery.js"></script>
	<script src="${base}/resources/mobile/shop/js/bootstrap.js"></script>
	<script src="${base}/resources/mobile/shop/js/jquery.rating.js"></script>
	<script src="${base}/resources/mobile/shop/js/jquery.validate.js"></script>
	<script src="${base}/resources/mobile/shop/js/underscore.js"></script>
	<script src="${base}/resources/mobile/shop/js/common.js"></script>
	<script type="text/javascript">
	$().ready(function() {
		
		var $reviewForm = $("#reviewForm");
		var $score = $("input.score");
		var $tips = $("#tips");
		var $captcha = $("#captcha");
		var $submit = $("button:submit");
		
		// 验证码图片
		$captcha.captchaImage();
		
		// 评分
		$score.rating({
			callback: function(value, link) {
				var $element = $(this);
				$tips.text($element.attr("title"));
			}
		});
		
		// 表单验证
		$reviewForm.validate({
			rules: {
				content: {
					required: true,
					maxlength: 200
				},
				captcha: "required"
			},
			submitHandler: function(form) {
				$.ajax({
					url: $reviewForm.attr("action"),
					type: "POST",
					data: $reviewForm.serialize(),
					dataType: "json",
					beforeSend: function() {
						$submit.prop("disabled", true);
					},
					success: function() {
						setTimeout(function() {
							location.href = "${base}/product/detail/${product.id}#review";
						}, 3000);
					},
					error: function(xhr, textStatus, errorThrown) {
						$captcha.captchaImage("refresh", true);
					},
					complete: function() {
						$submit.prop("disabled", false);
					}
				});
			}
		});
	
	});
	</script>
</head>
<body class="add-review">
	<header class="header-fixed">
		<a class="pull-left" href="${base}/product/detail/${product.id}#review">
			<span class="glyphicon glyphicon-menu-left"></span>
		</a>
		${message("shop.review.title")}
	</header>
	<main>
		<div class="media">
			<div class="media-left">
				<a href="${base}${product.path}">
					<img src="${product.thumbnail!setting.defaultThumbnailProductImage}" alt="${product.name}">
				</a>
			</div>
			<div class="media-body">
				<h4 class="media-heading">${abbreviate(product.name, 30, "...")}</h4>
				<span>
					${message("Product.price")}:
					<strong class="red">${currency(product.price, true, true)}</strong>
				</span>
				[#if product.scoreCount > 0]
					<span>${message("Product.score")}: ${product.score?string("0.0")}</span>
				[#elseif setting.isShowMarketPrice]
					<span>
						${message("Product.marketPrice")}:
						<del class="gray-darker">${currency(product.marketPrice, true, true)}</del>
					</span>
				[/#if]
			</div>
		</div>
		<form id="reviewForm" action="${base}/review/save" method="post">
			<input name="productId" type="hidden" value="${product.id}">
			<div class="form-group">
				<label>${message("Review.score")}</label>
				<input name="score" class="score" type="radio" value="1" title="${message("shop.review.score1")}">
				<input name="score" class="score" type="radio" value="2" title="${message("shop.review.score2")}">
				<input name="score" class="score" type="radio" value="3" title="${message("shop.review.score3")}">
				<input name="score" class="score" type="radio" value="4" title="${message("shop.review.score4")}">
				<input name="score" class="score" type="radio" value="5" title="${message("shop.review.score5")}" checked>
				<strong id="tips" class="tips orange-light">${message("shop.review.score5")}</strong>
			</div>
			<div class="form-group">
				<label for="content">${message("Consultation.content")}</label>
				<textarea id="content" name="content" class="form-control" rows="5"></textarea>
			</div>
			[#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("review")]
				<div class="form-group">
					<label for="captcha">${message("common.captcha.name")}</label>
					<div class="input-group">
						<input id="captcha" name="captcha" class="captcha form-control" type="text" maxlength="4" autocomplete="off">
						<span class="input-group-btn"></span>
					</div>
				</div>
			[/#if]
			<button class="btn btn-lg btn-primary btn-block" type="submit">${message("shop.review.submit")}</button>
		</form>
	</main>
</body>
</html>