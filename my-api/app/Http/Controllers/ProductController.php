<?php

namespace App\Http\Controllers;

use App\Models\Product;
use Illuminate\Http\Request;

class ProductController extends Controller
{
    //
    public function index(){
        return Product::all();
    }
    public function show($id)
    {
        return Product::find($id);
    }

    public function store(Request $request)
    {
        $data = $request->all();
        $request->validate([
            'title' => 'required|string',
            'price' => 'required|numeric|gt:0',
            'description' => 'required|string',
            'image' => 'required|string',
        ]);
        $data['category_id'] = 1;
        Product::create($data);
        return response()->json(['message' => 'Product created successfully']);
    }
}
